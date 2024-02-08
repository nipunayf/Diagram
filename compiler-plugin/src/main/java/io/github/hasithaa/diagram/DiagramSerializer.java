/*
 *  Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.github.hasithaa.diagram;

import io.ballerina.projects.Project;
import io.ballerina.projects.util.ProjectPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface DiagramSerializer {

    static void serialize(String fileName, String content, Project project) throws IOException {
        Path packageRootPath = ProjectPaths.packageRoot(project.sourceRoot());
        Path diagramDirectory = packageRootPath.resolve("diagrams");
        if (!Files.exists(diagramDirectory)) {
            Files.createDirectory(diagramDirectory);
        }
        Path diagram = diagramDirectory.resolve(fileName);
        if (!Files.exists(diagram)) {
            Files.createFile(diagram);
        }
        Files.writeString(diagram, content, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
