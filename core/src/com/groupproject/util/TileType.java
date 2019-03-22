package com.groupproject.util;

/**
 * @author Mohammed Al-Safwan
 * @date Mar 19, 2019
 */
public enum TileType {
	WALL, GROUND, NULL;
	
    public static TileType fromInteger(int index) {
        switch(index) {
        case 0:
            return WALL;
        case 1:
            return GROUND;
        case 2:
            return NULL;
        }
        return null;
    }
}
