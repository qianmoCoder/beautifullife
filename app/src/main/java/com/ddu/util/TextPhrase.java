package com.ddu.util;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.util.Stack;

public class TextPhrase {
	/** The unmodified original pattern. */
	@NonNull
    private final CharSequence pattern;
	/** Cached result after replacing all keys with corresponding values. */
	@Nullable
    private CharSequence formatted;
	/**
	 * The constructor parses the original pattern into this doubly-linked list
	 * of tokens.
	 */
	@Nullable
    private Token head;

	/** When parsing, this is the current character. */
	private char curChar;

	private String firstSeparator;
	private String secondSeparator;

	private int curCharIndex;

	private int outerColor;

	private int innerFirstColor;
	private int innerFirstSize;

	private int innerSecondColor;
	private int innerSecondSize;

	private char firstLeftSeparator;
	private char secondLeftSeparator;
	private char firstRightSeparator;
	private char secondRightSeparator;

	/** Indicates parsing is complete. */
	private static final int EOF = 0;

	/**
	 * Entry point into this API.
	 *
	 * @throws IllegalArgumentException
	 *             if pattern contains any syntax errors.
	 */
	@NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static TextPhrase from(@NonNull Fragment f, int patternResourceId) {
		return from(f.getResources(), patternResourceId);
	}

	/**
	 * Entry point into this API.
	 *
	 * @throws IllegalArgumentException
	 *             if pattern contains any syntax errors.
	 */
	@NonNull
    public static TextPhrase from(@NonNull View v, int patternResourceId) {
		return from(v.getResources(), patternResourceId);
	}

	/**
	 * Entry point into this API.
	 *
	 * @throws IllegalArgumentException
	 *             if pattern contains any syntax errors.
	 */
	@NonNull
    public static TextPhrase from(@NonNull Context c, int patternResourceId) {
		return from(c.getResources(), patternResourceId);
	}

	/**
	 * Entry point into this API.
	 *
	 * @throws IllegalArgumentException
	 *             if pattern contains any syntax errors.
	 */
	@NonNull
    public static TextPhrase from(@NonNull Resources r, int patternResourceId) {
		return from(r.getText(patternResourceId));
	}

	/**
	 * Entry point into this API; pattern must be non-null.
	 *
	 * @throws IllegalArgumentException
	 *             if pattern contains any syntax errors.
	 */
	@NonNull
    public static TextPhrase from(@NonNull CharSequence pattern) {
		return new TextPhrase(pattern);
	}

	private TextPhrase(@NonNull CharSequence pattern) {
		curChar = (pattern.length() > 0) ? pattern.charAt(0) : EOF;

		this.pattern = pattern;
		// Invalidate the cached formatted text.
		formatted = null;
		firstSeparator = "{}";// initialize the default separator
		secondSeparator = "[]";
		outerColor = 0xFF666666;// initialize the default value
		innerFirstColor = 0xFF333333;// initialize the default value
		innerSecondColor = 0xFF333333;

		innerFirstSize = 25;
		innerSecondSize = 25;
	}


	@NonNull
    public TextPhrase innerFirstColor(int innerColor) {
		this.innerFirstColor = innerColor;
		return this;
	}

	@NonNull
    public TextPhrase innerSecondColor(int innerColor) {
		this.innerSecondColor = innerColor;
		return this;
	}

	@NonNull
    public TextPhrase innerFirstSize(int innerSize) {
		this.innerFirstSize = innerSize;
		return this;
	}

	@NonNull
    public TextPhrase innerSecondSize(int innerSize) {
		this.innerSecondSize = innerSize;
		return this;
	}

	/**
	 * cut the pattern with the separators and linked them with double link
	 * structure;
	 */
	private void createDoubleLinkWithToken() {
		Token prev = null;
		Token next;
		while ((next = token(prev)) != null) {
			// Creates a doubly-linked list of tokens starting with head.
			if (head == null)
				head = next;
			prev = next;
		}
	}

	/**
	 * Returns the next token from the input pattern, or null when finished
	 * parsing.
	 */
	private Token token(Token prev) {
		if (curChar == EOF) {
			return null;
		}
		if (curChar == firstLeftSeparator) {
			char nextChar = lookahead();
			if (nextChar == firstLeftSeparator) {
				return leftSeparator(prev, nextChar);
			} else {
				return innerFirst(prev);
			}
		} else if (curChar == secondLeftSeparator) {
			char nextChar = lookahead();
			if (nextChar == secondLeftSeparator) {
				return leftSeparator(prev, nextChar);
			} else {
				return innerSecond(prev);
			}
		}
		return outer(prev);
	}

	private char getFirstLeftSeparator() {
		return firstSeparator.charAt(0);
	}

	private char getFirstRightSeparator() {
		if (firstSeparator.length() == 2) {
			return firstSeparator.charAt(1);
		}
		return firstSeparator.charAt(0);
	}

	private char getSecondLeftSeparator() {
		return secondSeparator.charAt(0);
	}

	private char getSecondRightSeparator() {
		if (secondSeparator.length() == 2) {
			return secondSeparator.charAt(1);
		}
		return secondSeparator.charAt(0);
	}

	/**
	 * Returns the text after replacing all keys with values.
	 *
	 * @throws IllegalArgumentException
	 *             if any keys are not replaced.
	 */
	@Nullable
    public CharSequence format() {
		if (formatted == null) {
			firstLeftSeparator = getFirstLeftSeparator();
			secondLeftSeparator = getSecondLeftSeparator();

			firstRightSeparator = getFirstRightSeparator();
			secondRightSeparator = getSecondRightSeparator();

			if (!checkPattern()) {
				throw new IllegalStateException("the separators don't match in the pattern!");
			}
			createDoubleLinkWithToken();
			// Copy the original pattern to preserve all spans, such as bold,
			// italic, etc.
			SpannableStringBuilder sb = new SpannableStringBuilder(pattern);
			for (Token t = head; t != null; t = t.next) {
				if (t instanceof OuterToken) {
					continue;
				}
				t.expand(sb);
			}

			formatted = sb;
		}
		return formatted;
	}

	/**
	 * check if the pattern has legal separators
	 *
	 * @return
	 */
	private boolean checkPattern() {
		if (pattern == null) {
			return false;
		}
		Stack<Character> separatorStack = new Stack<Character>();
		for (int i = 0; i < pattern.length(); i++) {
			char cur = pattern.charAt(i);
			if (cur == firstLeftSeparator || cur == secondLeftSeparator) {
				separatorStack.push(cur);
			} else if (cur == firstRightSeparator || cur == secondRightSeparator) {
				if (!separatorStack.isEmpty()) {
					char separator = separatorStack.pop();
					if ((separator == firstLeftSeparator || separator == secondLeftSeparator)) {
						continue;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return separatorStack.isEmpty();
	}

	@NonNull
    private InnerToken innerFirst(Token prev) {
		String key = inner(prev, firstRightSeparator);
		return new InnerToken(prev, key, innerFirstColor, innerFirstSize);
	}

	@NonNull
    private InnerToken innerSecond(Token prev) {
		String key = inner(prev, secondRightSeparator);
		return new InnerToken(prev, key, innerSecondColor, innerSecondSize);
	}

	@NonNull
    private String inner(Token prev, char separator) {

		// Store keys as normal Strings; we don't want keys to contain spans.
		StringBuilder sb = new StringBuilder();

		// Consume the left separator.
		consume();
		while (curChar != separator && curChar != EOF) {
			sb.append(curChar);
			consume();
		}

		if (curChar == EOF) {
			throw new IllegalArgumentException("Missing closing separator");
		}
		// consume the right separator.
		consume();

		if (sb.length() == 0) {
			throw new IllegalStateException("Disallow empty content between separators,for example {}");
		}

		String key = sb.toString();
		return key;
	}

	/** Consumes and returns a token for a sequence of text. */
	@NonNull
    private OuterToken outer(Token prev) {
		int startIndex = curCharIndex;

		while (curChar != firstLeftSeparator && curChar != secondLeftSeparator && curChar != EOF) {
			consume();
		}
		return new OuterToken(prev, curCharIndex - startIndex, outerColor);
	}

	/**
	 * Consumes and returns a token representing two consecutive curly brackets.
	 */
	@NonNull
    private LeftSeparatorToken leftSeparator(Token prev, char leftSeparator) {
		consume();
		consume();
		return new LeftSeparatorToken(prev, leftSeparator);
	}

	/** Returns the next character in the input pattern without advancing. */
	private char lookahead() {
		return curCharIndex < pattern.length() - 1 ? pattern.charAt(curCharIndex + 1) : EOF;
	}

	/**
	 * Advances the current character position without any error checking.
	 * Consuming beyond the end of the string can only happen if this parser
	 * contains a bug.
	 */
	private void consume() {
		curCharIndex++;
		curChar = (curCharIndex == pattern.length()) ? EOF : pattern.charAt(curCharIndex);
	}

	/**
	 * Returns the raw pattern without expanding keys; only useful for
	 * debugging. Does not pass through to {@link #format()} because doing so
	 * would drop all spans.
	 */
	@NonNull
    @Override
	public String toString() {
		return pattern.toString();
	}

	private abstract static class Token {
		@Nullable
        private final Token prev;
		private Token next;

		protected Token(@Nullable Token prev) {
			this.prev = prev;
			if (prev != null)
				prev.next = this;
		}

		/**
		 * Replace text in {@code target} with this token's associated value.
		 */
		abstract void expand(SpannableStringBuilder target);

		/** Returns the number of characters after expansion. */
		abstract int getFormattedLength();

		/** Returns the character index after expansion. */
		final int getFormattedStart() {
			if (prev == null) {
				// The first token.
				return 0;
			} else {
				// Recursively ask the predecessor node for the starting index.
				return prev.getFormattedStart() + prev.getFormattedLength();
			}
		}
	}

	/** Ordinary text between tokens. */
	private static class OuterToken extends Token {
		private final int textLength;
		private int color;

		OuterToken(Token prev, int textLength, int _color) {
			super(prev);
			this.textLength = textLength;
			this.color = _color;
		}

		@Override
		void expand(@NonNull SpannableStringBuilder target) {

			int startPoint = getFormattedStart();
			int endPoint = startPoint + textLength;
			target.setSpan(new ForegroundColorSpan(color), startPoint, endPoint, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		@Override
		int getFormattedLength() {
			return textLength;
		}
	}

	/** A sequence of two curly brackets. */
	private static class LeftSeparatorToken extends Token {
		private char leftSeparator;

		LeftSeparatorToken(Token prev, char _leftSeparator) {
			super(prev);
			leftSeparator = _leftSeparator;
		}

		@Override
		void expand(@NonNull SpannableStringBuilder target) {
			int start = getFormattedStart();
			target.replace(start, start + 2, String.valueOf(leftSeparator));
		}

		@Override
		int getFormattedLength() {
			// for example,,Replace "{{" with "{".
			return 1;
		}
	}

	private static class InnerToken extends Token {
		/** The InnerText without separators,like '{' and '}'. */
		private final String innerText;

		private int color;
		private int size;

		InnerToken(Token prev, String inner, int color, int size) {
			super(prev);
			this.innerText = inner;
			this.color = color;
			this.size = size;
		}

		@Override
		void expand(@NonNull SpannableStringBuilder target) {

			int replaceFrom = getFormattedStart();
			// Add 2 to account for the separators.
			int replaceTo = replaceFrom + innerText.length() + 2;
			target.replace(replaceFrom, replaceTo, innerText);
			target.setSpan(new ForegroundColorSpan(color), replaceFrom, replaceTo - 2,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			target.setSpan(new AbsoluteSizeSpan(size), replaceFrom, replaceTo - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		@Override
		int getFormattedLength() {
			return innerText.length();
		}
	}
}
