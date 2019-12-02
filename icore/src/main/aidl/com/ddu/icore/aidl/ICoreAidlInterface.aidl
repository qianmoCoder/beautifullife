// ICoreAidlInterface.aidl
package com.ddu.icore.aidl;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.aidl.ICoreAidlCallBackInterface;

// Declare any non-default types here with import statements

interface ICoreAidlInterface {

    void sendMessage(inout GodIntent godIntent);

    void registerListener(in ICoreAidlCallBackInterface listener);

    void unregisterListener(in ICoreAidlCallBackInterface listener);
}
