package com.xz.ska.presenter;

import com.xz.ska.base.BaseActivity;
import com.xz.ska.model.Model;

public class Presenter {
    private Model model;
    private BaseActivity view;

    public Presenter(BaseActivity view) {
        this.view = view;
        model = new Model();
    }


}
