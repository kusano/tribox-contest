package controllers

import javax.inject._
import models._
import play.api._
import play.api.mvc._

import scala.util.Random
import java.security.SecureRandom

import java.io.File
import scala.sys.process._

import scala.language.postfixOps

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's setting pages.
 */
@Singleton
class SettingController @Inject() (
    cc: ControllerComponents,
    configuration: Configuration,
    customerService: CustomerRepository) extends HomeController(cc, configuration) {

    /**
     * Setting: Setting / First setting / Other settings
     */
    def setting = Action {
        Ok(views.html.setting(getContestName, getContestDescription, getContestUrl, getFirebaseappContest, getFirebaseappContestApikey, getFirebaseappContestDatabaseURL, getFirebaseappContestMessagingsenderid))
    }

    def settingfirst = Action {
        Ok(views.html.settingfirst(getContestName, getContestDescription, getContestUrl, getFirebaseappContest, getFirebaseappContestApikey, getFirebaseappContestDatabaseURL, getFirebaseappContestMessagingsenderid))
    }

    def settingemail = Action {
        Ok(views.html.settingemail(getContestName, getContestDescription, getContestUrl, getFirebaseappContest, getFirebaseappContestApikey, getFirebaseappContestDatabaseURL, getFirebaseappContestMessagingsenderid))
    }

    def settingpassword = Action {
        Ok(views.html.settingpassword(getContestName, getContestDescription, getContestUrl, getFirebaseappContest, getFirebaseappContestApikey, getFirebaseappContestDatabaseURL, getFirebaseappContestMessagingsenderid))
    }

    def settingusername = Action {
        Ok(views.html.settingusername(getContestName, getContestDescription, getContestUrl, getFirebaseappContest, getFirebaseappContestApikey, getFirebaseappContestDatabaseURL, getFirebaseappContestMessagingsenderid))
    }

    // トークンを生成
    def genToken: String = {
        return new Random(new SecureRandom()).alphanumeric.take(32).mkString
    }
}
