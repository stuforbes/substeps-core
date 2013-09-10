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

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.technophobia.substeps.execution.ExecutionNodeVisitor;
import com.technophobia.substeps.execution.node.RootNode;
import com.technophobia.substeps.execution.node.StepNode;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *  Find all usages of a step in the execution node tree
 */
public class ExecutionNodeStepUsageAnalyser implements StepUsageAnalyser{


    private final Supplier<RootNode> executionNodeTreeSupplier;
    private final StatefulExecutionNodeVisitor<UsageResult> executionNodeVisitor;

    private final Map<String, Collection<StepNode>> stepNodes;

    public ExecutionNodeStepUsageAnalyser(Supplier<RootNode> executionNodeTreeSupplier, StatefulExecutionNodeVisitor<UsageResult> executionNodeVisitor) {

        this.executionNodeTreeSupplier = executionNodeTreeSupplier;
        this.executionNodeVisitor = executionNodeVisitor;

        this.stepNodes = Maps.newHashMap();
    }

    public Collection<StepNode> stepsWithLine(String line) {

        if(stepNodes.isEmpty()){
            buildStepNodeMap();
        }

        return stepNodes.containsKey(line) ? Collections.unmodifiableCollection(stepNodes.get(line)) : Collections.<StepNode>emptyList();
    }

    private void buildStepNodeMap() {
        RootNode rootNode = executionNodeTreeSupplier.get();

        executionNodeVisitor.prepareCleanVisit();
        rootNode.accept(executionNodeVisitor);
        stepNodes.putAll(executionNodeVisitor.getResult().stepUsages());
    }
}
