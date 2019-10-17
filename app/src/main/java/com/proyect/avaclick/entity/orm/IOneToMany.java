package com.proyect.avaclick.entity.orm;

public @interface IOneToMany {
	
	String[] joinColumn() default {};
	boolean cascade() default false;
	
}
