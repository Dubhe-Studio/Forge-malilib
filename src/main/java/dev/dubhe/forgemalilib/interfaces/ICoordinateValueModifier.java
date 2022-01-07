package dev.dubhe.forgemalilib.interfaces;

import dev.dubhe.forgemalilib.util.PositionUtils.CoordinateType;

public interface ICoordinateValueModifier
{
    /**
     * Modifies the existing value by the given amount
     * @param type
     * @param amount
     * @return
     */
    boolean modifyValue(CoordinateType type, int amount);

    /**
     * Sets the coordinate indicated by <b>type</b> to the value parsed from the string <b>newValue</b>
     * @param type
     * @param newValue
     * @return
     */
    boolean setValueFromString(CoordinateType type, String newValue);
}
