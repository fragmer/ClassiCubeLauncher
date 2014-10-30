package net.classicube.launcher;

// Possible "expected" outcomes of a sign-in process.
// For any unexpected ones, use SignInException.
public enum SignInResult {

    SUCCESS,
    WRONG_USER_OR_PASS,
    INCORRECT_TOKEN,
    MIGRATED_ACCOUNT,
    CONNECTION_ERROR,
    CHALLENGE_FAILED,
    EMAIL_UNACCEPTABLE;

    public static String getMessage(SignInResult result) {
        switch (result) {
            case WRONG_USER_OR_PASS:
                return "Wrong username or password.";
            case INCORRECT_TOKEN:
                return "An error occured logging you in to ClassiCube.net<br>Error: Incorrect Token.";
            case MIGRATED_ACCOUNT:
                return "Your account has been migrated. "
                        + "Use your Mojang account (email) to sign in.";
            case CONNECTION_ERROR:
                return "Connection problem. The website may be down.";
            case CHALLENGE_FAILED:
                return "Wrong answer to the security question. "
                        + "Try again, or reset the security question at "
                        + "<a href=\"https://account.mojang.com/me/changeSecretQuestions\">Mojang.com</a>";
            case EMAIL_UNACCEPTABLE:
                return "You cannot sign in to ClassiCube.net with an email address.<br><br>"
                        + "<font size=4>To play on ClassiCube.net servers:</font></b>"
                        + "<blockquote><b>Please use your ClassiCube.net username. Your Mojang account won't work here &mdash; "
                        + "you have to register an account at ClassiCube.net</b></blockquote><br>"
                        + "<font size=4><b>To play on Minecraft.net servers:</b></font>"
                        + "<blockquote><b>Click <i>[Switch to Minecraft.net]</i> first, and then use your Mojang account.</b></blockquote>";
            default:
                return result.name();
        }
    }
}
