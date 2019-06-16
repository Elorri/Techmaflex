package com.techmaflex.app.sertissage;

class SpinnerWidthController {

    private final Callback mCallback;
    Float tuyauWidth;
    Float jupeWidth;
    Float emboutWidth;

    interface Callback {
        void onGlobalWidthReady(float max);
    }

    public SpinnerWidthController(Callback callback) {
        mCallback = callback;
    }

    public void setTuyauWidth(float width) {
        this.tuyauWidth = width;
        onGlobalWidthReady();
    }

    public void setJupeWidth(float width) {
        this.jupeWidth = width;
        onGlobalWidthReady();
    }

    public void setEmboutWidth(float width) {
        this.emboutWidth = width;
        onGlobalWidthReady();
    }

    private void onGlobalWidthReady() {
        if (tuyauWidth != null && jupeWidth != null && emboutWidth != null) {
            mCallback.onGlobalWidthReady(Math.max(Math.max(tuyauWidth, jupeWidth), emboutWidth));
        }
    }

}
