package com.proyect.avaclick.entity;

import java.util.UUID;

import com.proyect.avaclick.entity.orm.IColumn;
import com.proyect.avaclick.entity.orm.IMappedSuperclass;

@IMappedSuperclass
public class BaseEntity {

    @IColumn(name = "_EntityID", dataType="text")
    public String _EntityID;

    @IColumn(name = "_RefenrenceEntityID", dataType="text")
    public String _RefenrenceEntityID;


    public BaseEntity() {
        this._EntityID = UUID.randomUUID().toString();
    }

}
