package de.hd.highlight

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.ui.JBColor
import com.intellij.ui.SimpleTextAttributes
import java.awt.Color
import javax.swing.Icon


class StatusIconDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val project: Project = node.project
        val virtualFile: VirtualFile = node.virtualFile ?: return

        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return
        val text = psiFile.text

        if(text.contains("// EXCLUDE FROM STATUS")) {
            return
        }
        if (text.contains("// STATUS: DONE")) {
            if(text.contains("// ICON")) {
                data.setIcon(DoneIcon)
            }
            val originalPresentation = data.presentableText
            data.clearText()
            data.addText(originalPresentation, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" [DONE]",
                SimpleTextAttributes(SimpleTextAttributes.STYLE_BOLD, JBColor(Color(0x389e34), Color(0x499c54))))
        } else if (text.contains("// STATUS: WIP")) {
            if(text.contains("// ICON")) {
                data.setIcon(WipIcon)
            }
            val originalPresentation = data.presentableText
            data.clearText()
            data.addText(originalPresentation, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" [IN PROGRESS]",
                SimpleTextAttributes(SimpleTextAttributes.STYLE_BOLD, JBColor(Color(0xd98a00), Color(0xe0a431))))
        } else if (text.contains("// STATUS: TODO")) {
            if(text.contains("// ICON")) {
                data.setIcon(TodoIcon)
            }
            val originalPresentation = data.presentableText
            data.clearText()
            data.addText(originalPresentation, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" [TODO]",
                SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, JBColor(Color(0x3d5afe), Color(0x539dfc))))
        } else if (text.contains("// STATUS: INACTIVE")) {
            if(text.contains("// ICON")) {
                data.setIcon(InactiveIcon)
            }
            val originalPresentation = data.presentableText
            data.clearText()
            data.addText(originalPresentation, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" [INACTIVE]", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }

    }

    object DoneIcon : Icon by AllIcons.General.GreenCheckmark
    object WipIcon : Icon by AllIcons.Process.Step_1
    object TodoIcon : Icon by AllIcons.General.TodoDefault
    object InactiveIcon : Icon by AllIcons.General.Error
}