package com.heritagewalk.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContentModerationService {

    // Configurable list of sensitive, profane, hate-speech, and abusive terms
    private static final List<String> PROHIBITED_TERMS = List.of(
            "abuse", "abusive", "asshole", "bastard", "bitch", "bloody hell",
            "bullshit", "cheat", "crap", "cunt", "damn", "dick",
            "fake site", "fraud", "fuck", "hate", "idiot", "kill",
            "moron", "nigger", "piss", "porn", "scam", "scumbag",
            "sex", "shit", "slut", "stupid", "suck", "terrorist", "whore"
    );

    private final Pattern prohibitedPattern;

    public static class SafetyCheckResult {
        private final boolean flagged;
        private final String matchedTerm;
        private final String message;

        public SafetyCheckResult(boolean flagged, String matchedTerm, String message) {
            this.flagged = flagged;
            this.matchedTerm = matchedTerm;
            this.message = message;
        }

        public boolean isFlagged() {
            return flagged;
        }

        public String getMatchedTerm() {
            return matchedTerm;
        }

        public String getMessage() {
            return message;
        }
    }

    public ContentModerationService() {
        // Compile regex with whole-word boundaries \b to prevent innocent substring matches
        StringBuilder sb = new StringBuilder();
        sb.append("\\b(");
        for (int i = 0; i < PROHIBITED_TERMS.size(); i++) {
            if (i > 0) sb.append("|");
            sb.append(Pattern.quote(PROHIBITED_TERMS.get(i)));
        }
        sb.append(")\\b");
        this.prohibitedPattern = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    }

    /**
     * Inspects text content for prohibited terms and suspicious patterns.
     * Preserves original comment while performing normalized analysis.
     */
    public SafetyCheckResult inspectContent(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new SafetyCheckResult(false, null, "Empty comment");
        }

        // Normalize text: remove accents, normalize Unicode, lowercase, unify whitespace
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[@#\\$%\\^\\*\\_\\-\\+]", " ") // Replace leetspeak symbols with space
                .replaceAll("\\s+", " ")
                .trim();

        Matcher matcher = prohibitedPattern.matcher(normalized);
        if (matcher.find()) {
            String matched = matcher.group();
            return new SafetyCheckResult(
                    true,
                    matched,
                    "Automated safety rule flagged prohibited term: [" + matched + "]"
            );
        }

        return new SafetyCheckResult(false, null, "Passed automated safety check");
    }

    /**
     * Validates review text constraints.
     */
    public void validateReviewLength(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Review comment cannot be empty. Please share your experience.");
        }
        String trimmed = text.trim();
        if (trimmed.length() < 5) {
            throw new IllegalArgumentException("Review comment must be at least 5 characters long.");
        }
        if (trimmed.length() > 2000) {
            throw new IllegalArgumentException("Review comment exceeds maximum allowed length of 2000 characters.");
        }
    }
}
