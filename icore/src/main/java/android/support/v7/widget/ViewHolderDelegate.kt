package android.support.v7.widget

abstract class ViewHolderDelegate private constructor() {

    init {
        throw UnsupportedOperationException("no instances")
    }

    companion object {

        fun setPosition(viewHolder: RecyclerView.ViewHolder, position: Int) {
            viewHolder.mPosition = position
        }
    }
}
