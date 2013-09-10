package com.technophobia.substeps.runner.usage;

import com.google.common.base.Supplier;
import com.technophobia.substeps.execution.node.RootNode;
import com.technophobia.substeps.model.Syntax;
import com.technophobia.substeps.runner.TagManager;
import com.technophobia.substeps.runner.TestParameters;
import com.technophobia.substeps.runner.builder.ExecutionNodeTreeBuilder;

/**
 * Given a folder containing feature files, and a {@link com.technophobia.substeps.model.Syntax} model,
 * return an Execution tree
 */
public class FeatureFolderAndSyntaxRootNodeSupplier implements Supplier<RootNode> {

    private final String featureFolderPath;
    private final Syntax syntax;

    public FeatureFolderAndSyntaxRootNodeSupplier(String featureFolderPath, Syntax syntax){
        this.featureFolderPath = featureFolderPath;
        this.syntax = syntax;
    }

    public RootNode get() {
        TestParameters testParameters = new TestParameters(new TagManager(""), syntax, featureFolderPath);
        testParameters.init(false);

        ExecutionNodeTreeBuilder treeBuilder = new ExecutionNodeTreeBuilder(testParameters);
        return treeBuilder.buildExecutionNodeTree("");
    }
}
