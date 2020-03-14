package com.ibcsprimax.hrm.common;


public class ItemResponse<T> extends BaseResponse{
    
    private T item;

    public ItemResponse() {
    }
    
    public ItemResponse(T item) {
        this.item = item;
    }

    
    
    public T getItem() {
        return item;
    }

    public void setItem(T t) {
        this.item = t;
    }
    
}
    
