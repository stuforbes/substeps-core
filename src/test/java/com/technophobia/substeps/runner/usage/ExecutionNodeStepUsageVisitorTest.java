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

import com.technophobia.substeps.execution.node.BasicScenarioNode;
import com.technophobia.substeps.execution.node.ScenarioNode;
import com.technophobia.substeps.execution.node.StepNode;
import com.technophobia.substeps.execution.node.SubstepNode;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecutionNodeStepUsageVisitorTest {

    private StatefulExecutionNodeVisitor<UsageResult> nodeVisitor;

    @Before
    public void initialise(){
        this.nodeVisitor = new ExecutionNodeStepUsageVisitor();
    }

    @Test
    public void visitingAStepNodeWillUpdateUsageResult(){

        String stepLine = "A Step";
        SubstepNode step = mock(SubstepNode.class);
        when(step.getLine()).thenReturn(stepLine);

        nodeVisitor.visit(step);

        Map<String,Set<StepNode>> results = nodeVisitor.getResult().stepUsages();
        assertThat(results.containsKey(stepLine), is(true));
        assertThat(results.get(stepLine), hasItem(step));
    }

    @Test
    public void visitingANonStepNodeWillNotUpdateUsageResult(){

        nodeVisitor.visit(mock(BasicScenarioNode.class));

        Map<String,Set<StepNode>> results = nodeVisitor.getResult().stepUsages();
        assertThat(results.isEmpty(), is(true));
    }

    @Test
    public void preparingForCleanRunWillWipeUsageResult(){

        String stepLine = "A Step";
        SubstepNode step = mock(SubstepNode.class);
        when(step.getLine()).thenReturn(stepLine);

        nodeVisitor.visit(step);
        assertThat(nodeVisitor.getResult().stepUsages().isEmpty(), is(false));

        nodeVisitor.prepareCleanVisit();
        assertThat(nodeVisitor.getResult().stepUsages().isEmpty(), is(true));
    }
}
