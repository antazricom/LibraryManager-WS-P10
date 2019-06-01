package com.antazri.bo.utils.contract;

import java.util.Date;

public interface IDateExtension {

    public Date extendEndDate(Date pOriginalDate, int pExtension);
    public int getMaximumExtension();
}
