import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "AdManager"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "be.objectify" %% "deadbolt-java" % "2.1-RC2",
    "com.typesafe" %% "play-plugins-mailer" % "2.1.0",
    "net.tanesha.recaptcha4j" % "recaptcha4j" % "0.0.7",
  	"com.amazonaws" % "aws-java-sdk" % "1.5.0"
            

  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here 
    //template import 
    templatesImport +="com.avaje.ebean._", 
    templatesImport += "models.dataWrapper._",
    templatesImport += "models.dataWrapper.zone._",    
    templatesImport += "models.dataWrapper.user._",    
    templatesImport += "models.dataWrapper.setting._",    
    templatesImport += "models.dataWrapper.campaign._",    
    templatesImport += "models.dataWrapper.finance._",    
    templatesImport += "models.dataWrapper.report._",    
    templatesImport += "models.data._",
    templatesImport += "models.form.frontendForm.LoginForm",
    templatesImport += "models.form.frontendForm._",
    templatesImport += "models.form.frontendForm.ForgetPassForm",
	templatesImport +="models.form.backendForm.zoneForm._",
    templatesImport +="models.form.backendForm.userForm._",
	templatesImport +="models.form.backendForm.campaignForm._",
    templatesImport +="models.form.backendForm.financeForm._",
       
    //Deadbolt
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)

  )

}
