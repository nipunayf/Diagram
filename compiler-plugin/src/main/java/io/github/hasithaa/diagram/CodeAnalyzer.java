package io.github.hasithaa.diagram;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.projects.ModuleId;
import io.ballerina.projects.plugins.AnalysisTask;
import io.ballerina.projects.plugins.CompilationAnalysisContext;
import io.github.hasithaa.diagram.flowchart.FlowChart;
import io.github.hasithaa.diagram.flowchart.FlowChartGenerator;
import io.github.hasithaa.diagram.integration.CodeVisitor;

import java.io.IOException;
import java.util.List;

public class CodeAnalyzer<T> implements AnalysisTask<CompilationAnalysisContext> {

    @Override
    public void perform(CompilationAnalysisContext ctx) {

        if (ctx.compilation().diagnosticResult().hasErrors()) {
            return;
        }
        ModuleId currentModuleId = ctx.currentPackage().getDefaultModule().moduleId();
        SemanticModel semanticModel = ctx.compilation().getSemanticModel(currentModuleId);
        CodeVisitor codeVisitor = new CodeVisitor(semanticModel);
        ctx.currentPackage().getDefaultModule().documentIds().forEach(documentId -> {
            ctx.currentPackage().getDefaultModule().document(documentId).syntaxTree().rootNode().accept(codeVisitor);
        });


        try {
            List<FlowChart> list = codeVisitor.getDiagrams().stream().map(FlowChartGenerator::generateFlowChart)
                                              .toList();
            DiagramSerializer.serialize(list, ctx.currentPackage().project());
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while generating the diagram for the function:", e);
        }

    }
}
