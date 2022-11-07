package com.github.neilyich.expressionskt.utils

import java.lang.Integer.min
import java.util.stream.Stream

class ClassUtils {
    companion object {

        @JvmStatic
        fun nearestSuperClass(left: Class<*>, right: Class<*>): Class<*> {
            if (left.isAssignableFrom(right)) return left
            if (right.isAssignableFrom(left)) return right
            val commonSuperClass = commonSuperClass(left, right)
            if (commonSuperClass != Any::class.java) return commonSuperClass
            return commonSuperInterface(left, right) ?: Any::class.java
        }

        @JvmStatic
        fun commonSuperClass(left: Class<*>, right: Class<*>): Class<*> {
            val leftSuperClasses = superClasses(left)
            val rightSuperClasses = superClasses(right)
            var commonSuperClass: Class<*> = Any::class.java
            for (i in 0 until min(leftSuperClasses.size, rightSuperClasses.size)) {
                if (leftSuperClasses[i] == rightSuperClasses[i]) {
                    commonSuperClass = leftSuperClasses[i]
                }
            }
            return commonSuperClass
        }

        @JvmStatic
        fun superClasses(clazz: Class<*>): List<Class<*>> {
            return Stream.iterate(clazz, { it.superclass != null }, { it.superclass }).toList().reversed()
        }

        @JvmStatic
        fun commonSuperInterface(left: Class<*>, right: Class<*>): Class<*>? {
            val leftSuperInterfaces = superInterfaces(left)
            val rightSuperInterfaces = superInterfaces(right)
            val commonInterfaces = leftSuperInterfaces.intersect(rightSuperInterfaces).toMutableSet()
            var commonInterface = findCommonInterface(commonInterfaces)
            while (commonInterface != null) {
                commonInterfaces.remove(commonInterface)
                val nextCommonInterface = findCommonInterface(commonInterfaces) ?: return commonInterface
                commonInterface = nextCommonInterface
            }
            return null
        }

        @JvmStatic
        fun superInterfaces(clazz: Class<*>): Set<Class<*>> {
            val interfaces = mutableSetOf<Class<*>>()
            val queue = ArrayDeque<Class<*>>()
            queue.addAll(clazz.interfaces)
            while (queue.isNotEmpty()) {
                val i = queue.removeFirst().interfaces
                interfaces.addAll(i)
                queue.addAll(i)
            }
            return interfaces
        }

        @JvmStatic
        private fun findCommonInterface(interfaces: Set<Class<*>>): Class<*>? {
            for (i in interfaces) {
                if (interfaces.stream().allMatch { i.isAssignableFrom(it) }) return i
            }
            return null
        }

        @JvmStatic
        fun superClasses1(clazz: Class<*>): List<Set<Class<*>>> {
            val result = mutableListOf<Set<Class<*>>>()
            result.add(setOf(clazz))
            val superClasses = ArrayDeque<Pair<Int, Class<*>>>()
            superClasses.addLast(0 to clazz)
            while (superClasses.isNotEmpty()) {
                val classesToProcess = mutableListOf<Class<*>>()
                val (currentLevel, baseClass) = superClasses.removeFirst()
                classesToProcess.add(baseClass)
                while (superClasses.isNotEmpty() && superClasses.first().first == currentLevel) {
                    classesToProcess.add(superClasses.removeFirst().second)
                }
                val level = mutableSetOf<Class<*>>()
                val nextLevel = currentLevel + 1
                classesToProcess.forEach { currentClass ->
                    currentClass.superclass?.let {
                        if (it != Any::class.java) {
                            superClasses.addLast(nextLevel to it)
                            level.add(it)
                        }
                    }
                    currentClass.interfaces.forEach {
                        superClasses.addLast(nextLevel to it)
                        level.add(it)
                    }
                }
                if (level.isNotEmpty()) {
                    result.add(level)
                }
            }
            result.add(mutableSetOf(Any::class.java))
            return result.reversed()
        }
    }
}