package specflow.server;

import specflow.common.SpecFlowConstants;

public class SpecFlowBean {
    public String getNUnitConsoleExe() {
        return SpecFlowConstants.NUNIT_CONSOLE_EXE_PATH;
    }

    public String getSpecFlowScenarioAssembly() {
        return SpecFlowConstants.SPECFLOW_SCENARIO_ASSEMBLY;
    }

    public String getSpecFlowExe() {
        return SpecFlowConstants.SPECFLOW_EXE_PATH;
    }

    public String getSpecFlowProjectFile() {
        return SpecFlowConstants.SPECFLOW_PROJECT_FILE;
    }
}
