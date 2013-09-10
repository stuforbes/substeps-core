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
import com.technophobia.substeps.execution.ExecutionNodeVisitor;
import com.technophobia.substeps.execution.node.RootNode;
import com.technophobia.substeps.execution.node.StepNode;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class ExecutionNodeStepUsageAnalyserTest {

    private Supplier executionNodeTreeSupplier;
    private RootNode rootNode;
    private StatefulExecutionNodeVisitor<UsageResult> executionNodeVisitor;

    private StepUsageAnalyser stepUsageAnalyser;

    @SuppressWarnings("unchecked")
    @Before
    public void initialise(){
        this.executionNodeTreeSupplier = mock(Supplier.class);
        this.rootNode = mock(RootNode.class);
        this.executionNodeVisitor = mock(StatefulExecutionNodeVisitor.class);

        this.stepUsageAnalyser = new ExecutionNodeStepUsageAnalyser(executionNodeTreeSupplier, executionNodeVisitor);
    }

    @Test
    public void returnsSingleItemListIfOneStepUsageFound(){

        String step = "A Step";
        UsageResult usageResult = resultFor(step, 1);

        when(executionNodeTreeSupplier.get()).thenReturn(rootNode);
        when(executionNodeVisitor.getResult()).thenReturn(usageResult);

        Collection<StepNode> stepNodes = stepUsageAnalyser.stepsWithLine(step);
        assertThat(stepNodes.size(), is(1));
    }

    @Test
    public void returnsMultiItemListIfManyStepUsageFound(){
        String step = "A Step";
        UsageResult usageResult = resultFor(step, 2);

        when(executionNodeTreeSupplier.get()).thenReturn(rootNode);
        when(executionNodeVisitor.getResult()).thenReturn(usageResult);


        Collection<StepNode> stepNodes = stepUsageAnalyser.stepsWithLine(step);
        assertThat(stepNodes.size(), is(2));
    }

    @Test
    public void returnsEmptyListIfNoStepUsagesFound(){
        String step = "A Step";
        UsageResult usageResult = new UsageResult();

        when(executionNodeTreeSupplier.get()).thenReturn(rootNode);
        when(executionNodeVisitor.getResult()).thenReturn(usageResult);

        Collection<StepNode> stepNodes = stepUsageAnalyser.stepsWithLine(step);
        assertThat(stepNodes.isEmpty(), is(true));
    }

    @Test
    public void doesNotRebuildExecutionNodeTreeBetweenStepCalls(){
        String step = "A Step";
        UsageResult usageResult = resultFor(step, 1);

        when(executionNodeTreeSupplier.get()).thenReturn(rootNode);
        when(executionNodeVisitor.getResult()).thenReturn(usageResult);

        Collection<StepNode> stepNodes = stepUsageAnalyser.stepsWithLine(step);
        assertThat(stepNodes.size(), is(1));

        stepUsageAnalyser.stepsWithLine(step);

        verify(executionNodeTreeSupplier, times(1)).get();
        verify(rootNode, times(1)).accept(executionNodeVisitor);
    }

    private UsageResult resultFor(String stepLine, int numberOfSteps) {
        UsageResult result = new UsageResult();
        for (int i=0; i<numberOfSteps; i++){
            result.registerStepFor(stepLine, mock(StepNode.class));
        }
        return result;
    }
}
