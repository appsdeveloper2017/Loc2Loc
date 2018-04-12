package com.appdesigndm.loc2loc.Callbacks;

public interface OnFinish {

    void run(OnFinishResultCode resultCode);

    enum OnFinishResultCode {
        SUCCES(1),
        FAILURE(0);

        private final int value;

        OnFinishResultCode(final int value) {
            this.value = value;
        }

    }
}
