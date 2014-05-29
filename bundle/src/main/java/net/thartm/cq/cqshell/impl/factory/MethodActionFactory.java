package net.thartm.cq.cqshell.impl.factory;

import com.google.common.base.Optional;
import net.thartm.cq.cqshell.action.ShellAction;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014
 */
public interface MethodActionFactory {

    Optional<ShellAction> findAction(final String methodName);
}
