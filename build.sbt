import AssemblyKeys._

jarName in assembly := "Space.jar"

name := "Space"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

resolvers += Resolver.sonatypeRepo("snapshots")

val jmeVersion = "3.0.0.20140325-SNAPSHOT"

libraryDependencies += "com.jme3" % "jmonkeyengine3" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-desktop" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-core" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-lwjgl" % jmeVersion

libraryDependencies += "com.jme3" % "lwjgl" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-lwjgl-natives" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-jbullet" % jmeVersion

libraryDependencies += "com.jme3" % "jbullet" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-blender" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-desktop" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-effects" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-networking" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-plugins" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-terrain" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-testdata" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-jogg" % jmeVersion

libraryDependencies += "com.jme3" % "j-ogg-oggd" % jmeVersion

libraryDependencies += "com.jme3" % "j-ogg-vorbisd" % jmeVersion

libraryDependencies += "com.jme3" % "jinput" % jmeVersion

libraryDependencies += "com.jme3" % "eventbus" % jmeVersion

libraryDependencies += "com.jme3" % "stack-alloc" % jmeVersion

libraryDependencies += "com.jme3" % "vecmath" % jmeVersion

libraryDependencies += "com.jme3" % "xmlpull-xpp3" % jmeVersion

libraryDependencies += "com.jme3" % "jME3-niftygui" % jmeVersion

libraryDependencies += "com.jme3" % "nifty" % jmeVersion

libraryDependencies += "com.jme3" % "nifty-default-controls" % jmeVersion

libraryDependencies += "com.jme3" % "nifty-examples" % jmeVersion

libraryDependencies += "com.jme3" % "nifty-style-black" % jmeVersion

