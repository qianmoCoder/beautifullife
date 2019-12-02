// ICoreAidlCallBackInterface.aidl
package com.ddu.icore.aidl;

import com.ddu.icore.aidl.GodIntent;

// Declare any non-default types here with import statements

interface ICoreAidlCallBackInterface {

    void callback(inout GodIntent godIntent);
}
