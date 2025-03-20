package com.ang.Hittable;

import com.ang.Maths.*;
import com.ang.HitRecord;

public abstract class Hittable {
	public abstract boolean hit(Ray r, Interval tInterval, HitRecord rec);
}

