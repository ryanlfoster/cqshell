package net.thartm.cq.cqshell;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 07/2014
 */
public enum ShellConstants {

    RESOURCE_TYPE("cqshell/components/console"),
    PATH("/apps/cqshell/content/console");

    private String value;

    ShellConstants(String value) {
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}
