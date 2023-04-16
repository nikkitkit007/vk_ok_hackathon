package org.statter.aspects.dataSending;

import java.sql.Timestamp;

public class NetworkData {
    private String StackTrace;
    private String RuntimeString;
    private String StartTimeString;
    private String FinishTimeString;
    private int PackageSize;

    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }

    public String getRunTime() {
        return RuntimeString;
    }

    public void setRunTime(String RuntimeString) {
        this.RuntimeString = RuntimeString;
    }

    public String getStartTime() {
        return StartTimeString;
    }

    public void setStartTime(String startTime) {
        StartTimeString = startTime;
    }

    public String getFinishTime() {
        return FinishTimeString;
    }

    public void setFinishTime(String finishTime) {
        FinishTimeString = finishTime;
    }

    public int getPackageSize() {
        return PackageSize;
    }

    public void setPackageSize(int packageSize) {
        PackageSize = packageSize;
    }
}
