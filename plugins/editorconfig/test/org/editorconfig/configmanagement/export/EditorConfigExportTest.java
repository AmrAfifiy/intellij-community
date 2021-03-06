// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.editorconfig.configmanagement.export;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.testFramework.LightPlatformTestCase;
import com.intellij.util.LineSeparator;
import org.editorconfig.language.EditorConfigLanguage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditorConfigExportTest extends LightPlatformTestCase {
  public void testWriter() throws IOException {

    CodeStyleSettings setting = CodeStyleSettings.getDefaults().clone();
    setting.LINE_SEPARATOR = LineSeparator.CRLF.getSeparatorString();
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try (EditorConfigSettingsWriter writer = new EditorConfigSettingsWriter(getProject(), output, setting).forLanguages(EditorConfigLanguage.INSTANCE)) {
      writer.writeSettings();
    }
    String result = output.toString("UTF-8");
    assertEquals(
      "[*]\n" +
      "charset=utf-8\n" +
      "end_of_line = crlf\n" +
      "insert_final_newline=false\n" +
      "max_line_length = 120\n" +
      "ij_formatter_off_tag = @formatter:off\n" +
      "ij_formatter_on_tag = @formatter:on\n" +
      "ij_formatter_tags_enabled = false\n" +
      "ij_wrap_on_typing = false\n" +
      "\n" +
      "[.editorconfig]\n" +
      "ij_editorconfig_align_group_field_declarations = false\n" +
      "ij_editorconfig_space_after_colon = false\n" +
      "ij_editorconfig_space_after_comma = true\n" +
      "ij_editorconfig_space_before_colon = false\n" +
      "ij_editorconfig_space_before_comma = false\n" +
      "ij_editorconfig_spaces_around_assignment_operators = true\n",

      result);
  }

}
