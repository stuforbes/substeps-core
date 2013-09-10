/*
 *	Copyright Technophobia Ltd 2012
 *
 *   This file is part of Substeps.
 *
 *    Substeps is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Substeps is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with Substeps.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.technophobia.substeps.runner.usage;

import com.technophobia.substeps.execution.AbstractExecutionNodeVisitor;
import com.technophobia.substeps.execution.node.IExecutionNode;
import com.technophobia.substeps.execution.node.StepNode;
import com.technophobia.substeps.execution.node.TaggedNode;

/**
 * Visits nodes in an execution node tree and reports step usage
 */
public class ExecutionNodeStepUsageVisitor extends AbstractExecutionNodeVisitor<Void> implements StatefulExecutionNodeVisitor<UsageResult>{

    private final UsageResult usageResult;

    public ExecutionNodeStepUsageVisitor(){
        this.usageResult = new UsageResult();
    }

    @Override
    public Void visit(IExecutionNode node) {
        if(node instanceof StepNode){
            StepNode stepNode = (StepNode) node;
            usageResult.registerStepFor(stepNode.getLine(), stepNode);
        }
        return null;
    }

    public void prepareCleanVisit() {
        usageResult.clearSteps();
    }

    public UsageResult getResult() {
        return usageResult;
    }
}
