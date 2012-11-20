package specflow.agent;

import com.intellij.execution.configurations.GeneralCommandLine;
import jetbrains.buildServer.ExecResult;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.SimpleCommandLineProcessRunner;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildProgressLogger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpecFlowBuildProcess implements BuildProcess {
    private final AgentRunningBuild agentRunningBuild;
    private final AtomicBoolean interrupted = new AtomicBoolean();
    private final AtomicBoolean finished = new AtomicBoolean();
    private final BuildProgressLogger logger;

    public SpecFlowBuildProcess(AgentRunningBuild agentRunningBuild) {
        this.agentRunningBuild = agentRunningBuild;
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
           RunScenarios();

           if (isInterrupted())
               return BuildFinishedStatus.INTERRUPTED;

           return BuildFinishedStatus.FINISHED_SUCCESS;
       } finally {
           finished.set(true);
       }
    }

    private void RunScenarios() {
        GeneralCommandLine generalCommandLine = new GeneralCommandLine();
        generalCommandLine.setExePath("D:\\Tools\\NUnit\\2.6.2\\nunit-console.exe");
        generalCommandLine.addParameters("SpecFlowSample\\bin\\Release\\SpecFlowSample.dll", "/labels", "/xml=TestResult.xml");
        generalCommandLine.setWorkingDirectory(agentRunningBuild.getCheckoutDirectory());
        logger.message("Command line: " + generalCommandLine.getCommandLineString());

        final ExecResult result = SimpleCommandLineProcessRunner.runCommand(generalCommandLine, null);
        for (String line : result.getOutLines()) {
            logger.message(line);
        }
        logger.message("Exit code: " + result.getExitCode());
    }
}
