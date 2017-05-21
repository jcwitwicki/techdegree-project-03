package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private Board board;
    private User user;
    private User questioner;
    private User answerer;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

//        Create additional objects which can be shared across tests.

        board = new Board("Java");
        user = board.createUser("user");
        questioner = board.createUser("questioner");
        answerer = board.createUser("answerer");
        question = questioner.askQuestion("New question");
        answer = answerer.answerQuestion(question, "New answer");
    }

    // Write a test to ensure that the questioner’s reputation goes up by 5 points if their question is upvoted.
    @Test
    public void questionerReputationGoesUpBy5IfQuestionUpVoted() throws Exception {
       user.upVote(question);

       assertEquals("+5 if question upvoted", 5, questioner.getReputation());
    }

    // Write a test to assert that the answerer’s reputation goes up by 10 points if their answer is upvoted.
    @Test
    public void answererReputationGoesUpBy10IfAnswerUpVoted() throws Exception {
        user.upVote(answer);

        assertEquals("+10 if answer upvoted", 10, answerer.getReputation());
    }

//    Write a test that proves that having an answer accepted gives the answerer a 15 point reputation boost.
    @Test
    public void answererReputationGoesUpBy15IfAnswerAccepted() throws Exception {
        questioner.acceptAnswer(answer);

        assertEquals("+15 if answer accepted", 15, answerer.getReputation());
    }

    //Using a test, ensure that voting either up or down is not allowed on questions
    // or answers by the original author, you know to avoid gaming the system.
    // Ensure the proper exceptions are being thrown.
    @Test
    public void upVoteOnQuestionNotAllowedByAuthor() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        questioner.upVote(question);
    }

    @Test
    public void upVoteOnAnswerNotAllowedByAuthor() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        answerer.upVote(answer);
    }

    @Test
    public void downVoteOnQuestionNotAllowedByAuthor() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        questioner.downVote(question);
    }

    @Test
    public void downVoteOnAnswerNotAllowedByAuthor() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        answerer.downVote(answer);
    }

    // Write a test to make sure that only the original questioner can accept an answer.
    // Ensure the intended messaging is being sent to back to the caller.
    @Test
    public void onlyOriginalQuestionerCanAcceptAnswer() throws Exception {
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only " + questioner.getName() + " can accept this answer as it is their question");
        user.acceptAnswer(answer);
    }

    // Reviewing the User.getReputation method may expose some code that is not requested
    // to be tested in the Meets Project instructions. Write the missing test.
    @Test
    public void questionerReputationDoesNotChangeIfQuestionDownVoted() throws Exception {
        user.downVote(question);

        assertEquals("no change if question downvoted", 0, questioner.getReputation());
    }

    @Test
    public void answererReputationGoesDownBy1IfAnswerIsDownVoted() throws Exception {
        user.downVote(answer);

        assertEquals("-1 if answer downvoted", -1, answerer.getReputation());
    }
}