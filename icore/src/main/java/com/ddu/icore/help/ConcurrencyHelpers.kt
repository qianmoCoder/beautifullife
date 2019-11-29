package com.ddu.icore.help

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicReference


class SingleRunner {

    private val mutex = Mutex()

    /**
     * 将下一个任务入队
     * class ProductsRepository(val productsDao: ProductsDao, val productsApi: ProductsService) {
     *      var singleRunner = SingleRunner<>()
     *
     *      suspend fun loadSortedProducts(ascending: Boolean): List<ProductListing> {
     *          // wait for the previous sort to complete before starting a new one
     *          return singleRunner.afterPrevious {
     *              if (ascending) {
     *                  productsDao.loadProductsByDateStockedAscending()
     *              } else {
     *                  productsDao.loadProductsByDateStockedDescending()
     *              }
     *           }
     *       }
     *   }
     *
     * */
    suspend fun <T> afterPrevious(block: suspend () -> T): T {
        mutex.withLock {
            return block()
        }
    }
}

class ControllerRunner<T> {

    private val activeTask = AtomicReference<Deferred<T>?>(null)

    /**
     * 取消前一个任务
     * class ProductsRepository(val productsDao: ProductsDao, val productsApi: ProductsService) {
     *      var controlledRunner = ControlledRunner<List<ProductListing>>()
     *
     *      suspend fun loadSortedProducts(ascending: Boolean): List<ProductListing> {
     *          // cancel the previous sorts before starting a new one
     *          return controlledRunner.cancelPreviousThenRun {
     *              if (ascending) {
     *                  productsDao.loadProductsByDateStockedAscending()
     *              } else {
     *                  productsDao.loadProductsByDateStockedDescending()
     *              }
     *           }
     *       }
     *   }
     *
     * */
    suspend fun cancelPreviousThenRun(block: suspend () -> T): T {
        activeTask.get()?.cancelAndJoin()
        return coroutineScope {
            val newTask = async(start = CoroutineStart.LAZY) {
                block()
            }
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

            val result: T
            while (true) {
                if (!activeTask.compareAndSet(null, newTask)) {
                    activeTask.get()?.cancelAndJoin()
                    yield()
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }

    /**
     * 加入前一个任务
     * class ProductsRepository(val productsDao: ProductsDao, val productsApi: ProductsService) {
     *      var controlledRunner = ControlledRunner<List<ProductListing>>()
     *
     *      suspend fun fetchProductsFromBackend(): List<ProductListing> {
     *           // if there's already a request running, return the result from the
     *           // existing request. If not, start a new request by running the block.
     *          return controlledRunner.joinPreviousOrRun {
     *              val result = productsApi.getProducts()
     *              productsDao.insertAll(result)
     *              result
     *           }
     *       }
     *   }
     *
     * */
    suspend fun joinPreviousOrRun(block: suspend () -> T): T {
        activeTask.get()?.let {
            return it.await()
        }
        return coroutineScope {
            val newTask = async(start = CoroutineStart.LAZY) {
                block()
            }
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }
            val result: T
            while (true) {
                if (!activeTask.compareAndSet(null, newTask)) {
                    val currentTask = activeTask.get()
                    if (currentTask != null) {
                        newTask.cancel()
                        result = currentTask.await()
                        break
                    } else {
                        yield()
                    }
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }
}