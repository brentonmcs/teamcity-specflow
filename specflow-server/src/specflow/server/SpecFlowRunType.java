package specflow.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jetbrains.buildServer.requirements.Requirement;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;

import org.jetbrains.annotations.NotNull;

public class SpecFlowRunType extends RunType {
  private PluginDescriptor myPluginDescriptor;

  public SpecFlowRunType(final RunTypeRegistry runTypeRegistry, final PluginDescriptor pluginDescriptor) {
    myPluginDescriptor = pluginDescriptor;
    runTypeRegistry.registerRunType(this);
  }

  @Override
  public PropertiesProcessor getRunnerPropertiesProcessor() {
    return new SpecFlowRunTypePropertiesProcessor();
  }

  @Override
  public String getDescription() {
    return SpecFlowConstants.RUNNER_DESCRIPTION;
  }

  @Override
  public String getEditRunnerParamsJspFilePath() {
    return null;
  }

  @Override
  public String getViewRunnerParamsJspFilePath() {
    return null;
  }

  @Override
  public Map<String, String> getDefaultRunnerProperties() {
      return new HashMap<String, String>();
  }

  @Override
  @NotNull
  public String getType() {
    return SpecFlowConstants.RUNNER_TYPE;
  }

  @Override
  public String getDisplayName() {
    return SpecFlowConstants.RUNNER_DISPLAY_NAME;
  }

  @NotNull
  @Override
  public String describeParameters(@NotNull final Map<String, String> parameters) {
    StringBuilder result = new StringBuilder();
    return result.toString();
  }

  @Override
  public List<Requirement> getRunnerSpecificRequirements(@NotNull final Map<String, String> runParameters) {
    return SpecFlowRequirementsUtil.getSpecFlowRequirements(runParameters);
  }
}
