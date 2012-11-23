package specflow.server;

import specflow.common.SpecFlowConstants;

public class SpecFlowBean {
    public String getNUnitConsoleExe() {
        return SpecFlowConstants.NUNIT_CONSOLE_EXE_PATH;
    }

    public String getSpecFlowScenarioAssembly() {
        return SpecFlowConstants.SPECFLOW_SCENARIO_ASSEMBLY;
    }
}
