package specflow.agent;

import com.intellij.execution.configurations.GeneralCommandLine;
import jetbrains.buildServer.ExecResult;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.SimpleCommandLineProcessRunner;
import jetbrains.buildServer.agent.*;
import org.jetbrains.annotations.NotNull;
import specflow.common.SpecFlowConstants;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpecFlowBuildProcess implements BuildProcess {
    private final AgentRunningBuild agentRunningBuild;
    private BuildRunnerContext buildRunnerContext;
    private final AtomicBoolean interrupted = new AtomicBoolean();
    private final AtomicBoolean finished = new AtomicBoolean();
    private final BuildProgressLogger logger;

    public SpecFlowBuildProcess(AgentRunningBuild agentRunningBuild, BuildRunnerContext buildRunnerContext) {
        this.agentRunningBuild = agentRunningBuild;
        this.buildRunnerContext = buildRunnerContext;
        logger = agentRunningBuild.getBuildLogger();
    }

    @Override
    public void start() throws RunBuildException {
    }

    @Override
    public boolean isInterrupted() {
        return interrupted.get();
    }

    @Override
    public boolean isFinished() {
        return finished.get();
    }

    @Override
    public void interrupt() {
        interrupted.set(true);
    }

    @NotNull
    @Override
    public BuildFinishedStatus waitFor() throws RunBuildException {
       try {
           Map<String,String> parameters = buildRunnerContext.getRunnerParameters();

           String nunitConsoleExePath = parameters.get(SpecFlowConstants.NUNIT_CONSOLE_EXE_PATH);
           String assemblyPath = parameters.get(SpecFlowConstants.SPECFLOW_SCENARIO_ASSEMBLY);

           RunScenarios(nunitConsoleExePath, assemblyPath);

           if (isInterrupted())
               return BuildFinishedStatus.INTERRUPTED;

           String specFlowExePath = parameters.get(SpecFlowConstants.SPECFLOW_EXE_PATH);
           String projectFile = parameters.get(SpecFlowConstants.SPECFLOW_PROJECT_FILE);

           GenerateReport(specFlowExePath, projectFile);

           if (isInterrupted())
               return BuildFinishedStatus.INTERRUPTED;

           return BuildFinishedStatus.FINISHED_SUCCESS;
       } finally {
           finished.set(true);
       }
    }

    private void RunScenarios(String exePath, String assemblyPath) {
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath(exePath);
        commandLine.addParameters(assemblyPath, "/labels", "/out=TestResult.txt", "/xml=TestResult.xml");
        commandLine.setWorkingDirectory(agentRunningBuild.getCheckoutDirectory());

        ExecuteCommand(commandLine);
    }

    private void GenerateReport(String exePath, String projectFile) {
        GeneralCommandLine generalCommandLine = new GeneralCommandLine();
        generalCommandLine.setExePath(exePath);
        generalCommandLine.addParameters("nunitexecutionreport", projectFile);
        generalCommandLine.setWorkingDirectory(agentRunningBuild.getCheckoutDirectory());

        ExecuteCommand(generalCommandLine);
    }

    private void ExecuteCommand(GeneralCommandLine commandLine) {
        logger.message("Command line: " + commandLine.getCommandLineString());

        final ExecResult result = SimpleCommandLineProcessRunner.runCommand(commandLine, null);

        for (String line : result.getOutLines()) {
            logger.message(line);
        }
        logger.message("Exit code: " + result.getExitCode());
    }
}
