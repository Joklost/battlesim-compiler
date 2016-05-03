package com.company.Steps;

import com.company.Objects.DynamicObjects.DynObj;

/**
 * Created by Magnus on 03-05-2016.
 */
public class WaitStep extends Step{
    private int time;
    private int ticker = 0;

    public WaitStep(DynObj object, int time){
        super(object);
        this.time = time;
    }

    public void Run(){
        object.Take(this);
        if(ticker == time){
            object.Release();
            isDone = true;
        }
        ticker++;
    }
}
