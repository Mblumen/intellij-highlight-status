package de.hd.highlight

import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.ide.projectView.PresentationData
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.psi.PsiManager
import com.intellij.util.PlatformIcons
import javax.swing.Icon
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ui.SimpleTextAttributes

class StatusIconDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val project: Project = node.project
        val virtualFile: VirtualFile = node.virtualFile ?: return

        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return
        val text = psiFile.text

        if (text.contains("// STATUS: DONE")) {
            data.setIcon(DoneIcon)
            val originalPresentation = data.presentableText
            data.clearText()
            data.addText(originalPresentation, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" [DONE]", SimpleTextAttributes.GRAY_ATTRIBUTES)
        } else if (text.contains("// STATUS: WIP")) {
            data.setIcon(WipIcon)
            val originalPresentation = data.presentableText
            data.clearText()
            data.addText(originalPresentation, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" [IN PROGRESS]", SimpleTextAttributes.SYNTHETIC_ATTRIBUTES)
        }
    }

    object DoneIcon : Icon by PlatformIcons.CHECK_ICON
    object WipIcon : Icon by PlatformIcons.CHECK_ICON_SELECTED
}