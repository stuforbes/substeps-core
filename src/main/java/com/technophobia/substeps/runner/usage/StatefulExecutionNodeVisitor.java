package com.technophobia.substeps.runner.usage;

import com.technophobia.substeps.execution.ExecutionNodeVisitor;

public interface StatefulExecutionNodeVisitor<Result> extends ExecutionNodeVisitor<Void> {

    void prepareCleanVisit();

    Result getResult();
}
