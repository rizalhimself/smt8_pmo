package com.rizalhimself.pmo23_praktikum.ui.mahasiswa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MahasiswaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MahasiswaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ini fragment mahasiswa!!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}