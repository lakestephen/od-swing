package com.od.swing.progress;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Nick
 * Date: 26/12/10
 * Time: 09:02
 * To change this template use File | Settings | File Templates.
 */
public class AnimatedIconTree extends JTree {

    private Set<AnimatorWrapper> animatorWrappers = new HashSet<AnimatorWrapper>();
    private AnimationTreeModelListener modelListener = new AnimationTreeModelListener();

    public AnimatedIconTree(TreeModel model) {
        super(model);
        initializeAnimators(model);
    }

    public AnimatedIconTree() {
        initializeAnimators(getModel());
    }

    public void setModel(TreeModel m) {
        if ( getModel() != null) {
            getModel().removeTreeModelListener(modelListener);
            initializeAnimators(m);
        }
        super.setModel(m);
    }

    private void initializeAnimators(TreeModel treeModel) {
        disposeAnimatorWrappers();
        animatorWrappers = new HashSet<AnimatorWrapper>();
        treeModel.addTreeModelListener(modelListener);

        TreeNode m = (TreeNode) treeModel.getRoot();
        addAnimatorWrappers(m);
    }

    private void addAnimatorWrappers(TreeNode m) {
        Enumeration e = m.children();
        while(e.hasMoreElements()) {
            addAnimatorWrappers((TreeNode)e.nextElement());
        }

        if ( m instanceof ProgressTreeNode) {
            AnimatorWrapper w = findOrCreateWrapper((ProgressTreeNode) m);
            w.addProgressNode((ProgressTreeNode) m);
        }
    }

    private AnimatorWrapper findOrCreateWrapper(ProgressTreeNode m) {
        AnimatorWrapper result = findAnimatorWrapper(m);
        if ( result == null) {
            result = new AnimatorWrapper(m.getIconComponentAnimator());
            animatorWrappers.add(result);
        }
        return result;
    }

    private AnimatorWrapper findAnimatorWrapper(ProgressTreeNode m) {
        AnimatorWrapper result = null;
        for ( AnimatorWrapper w : animatorWrappers) {
            if ( w.isAnimatingNode(m)) {
                result = w;
                break;
            }
        }
        return result;
    }

    private void disposeAnimatorWrappers() {
        for ( AnimatorWrapper w : animatorWrappers) {
            w.dispose();
        }
    }

    private class AnimationTreeModelListener implements TreeModelListener {

        public void treeNodesChanged(TreeModelEvent e) {
            for (Object child : e.getChildren()) {
                if ( child instanceof ProgressTreeNode) {
                    AnimatorWrapper w = findAnimatorWrapper((ProgressTreeNode)child);
                    w.nodeChanged((ProgressTreeNode)child);
                }
            }
        }

        public void treeNodesInserted(TreeModelEvent e) {
            for (Object child : e.getChildren()) {
                if ( child instanceof ProgressTreeNode) {
                    AnimatorWrapper w = findAnimatorWrapper((ProgressTreeNode)child);
                    w.addProgressNode((ProgressTreeNode)child);
                }
            }
        }

        public void treeNodesRemoved(TreeModelEvent e) {
            for (Object child : e.getChildren()) {
                if ( child instanceof ProgressTreeNode) {
                    AnimatorWrapper w = findAnimatorWrapper((ProgressTreeNode)child);
                    w.removeProgressNode((ProgressTreeNode)child);
                }
            }
        }

        public void treeStructureChanged(TreeModelEvent e) {
            initializeAnimators(getModel());
        }
    }


    /**
     * ProgressTreeNode must store the tree bounds when rendered
     * and return it via getBounds(s)
     */
    public static interface ProgressTreeNode {

        IconComponentAnimator getIconComponentAnimator();

        Rectangle getBounds();

        boolean isAnimationEnabled();

        void setAnimatedIcon(Icon i);
    }


    private class AnimatorWrapper implements IconComponentAnimator.IconComponent {

        private IconComponentAnimator animator;
        private Set<ProgressTreeNode> progressNodes = new HashSet<ProgressTreeNode>();
        private Set<ProgressTreeNode> animatedNodes = new HashSet<ProgressTreeNode>();

        private AnimatorWrapper(IconComponentAnimator animator) {
            this.animator = animator;
            animator.setAnimatedComponent(this);
        }

        public void setIcon(Icon i) {
            for ( ProgressTreeNode n : progressNodes) {
                if ( n.isAnimationEnabled()) {
                    n.setAnimatedIcon(i);
                    if ( n.getBounds() != null) {
                        AnimatedIconTree.this.repaint(n.getBounds());
                    }
                }
            }
        }

        public boolean isAnimatingNode(ProgressTreeNode n) {
            return n.getIconComponentAnimator() == animator;
        }

        public void nodeChanged(ProgressTreeNode n) {
            if ( n.isAnimationEnabled()) {
                addToAnimated(n);
            } else {
                removeFromAnimated(n);
            }
        }

        public void addProgressNode(ProgressTreeNode n) {
            progressNodes.add(n);
            if ( n.isAnimationEnabled()) {
                addToAnimated(n);
            }
        }

        public void removeProgressNode(ProgressTreeNode n) {
            progressNodes.remove(n);
            if ( n.isAnimationEnabled() ) {
                removeFromAnimated(n);
            }
        }

        private void addToAnimated(ProgressTreeNode n) {
            boolean added = animatedNodes.add(n);
            if ( added && animatedNodes.size() == 1) {
                animator.runAnimation();
            }
        }

        private void removeFromAnimated(ProgressTreeNode n) {
            boolean removed = animatedNodes.remove(n);
            if ( removed && animatedNodes.size() == 0) {
                animator.stopAnimation();
            }
        }

        public void addAncestorListener(AncestorListener l) {
            AnimatedIconTree.this.addAncestorListener(l);
        }

        public void removeAncestorListener(AncestorListener l) {
            AnimatedIconTree.this.removeAncestorListener(l);
        }

        public void dispose() {
            animator.stopAnimation();
        }
    }
}
