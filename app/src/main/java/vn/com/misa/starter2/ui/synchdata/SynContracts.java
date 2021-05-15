package vn.com.misa.starter2.ui.synchdata;

public interface SynContracts {
    interface View{
        void onStartSync();
        void onSuccess();
        void onFailure();
    }
    interface Presenter{
        void onStart();
        void onSuccess();
        void onFailure();
    }
    interface Model{
        void onStart();
        void onSuccess();
        void onFailure();
    }
}
