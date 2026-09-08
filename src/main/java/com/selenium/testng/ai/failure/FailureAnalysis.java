package com.selenium.testng.ai.failure;

import java.util.List;

// POJO to be used for AI's expected response
/**
 * 
 * { "failureType": "TIMEOUT", "rootCause": "Element was not available within
 * the configured timeout.", "confidence": 0.72, "possibleCauses": [
 * "Application response was slow", "Element locator may be incorrect", "Network
 * latency may have delayed page rendering" ] }
 * 
 * @author Arzoo Hingorani
 *
 */
public class FailureAnalysis {

	private String failureType;
	private String rootCause;
	private double confidence;
	private String explanation;
	private List<String> possibleCauses;
	private String suggestedAction;
	private List<String> evidence;

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public List<String> getPossibleCauses() {
		return possibleCauses;
	}

	public void setPossibleCauses(List<String> possibleCauses) {
		this.possibleCauses = possibleCauses;
	}

	public String getSuggestedAction() {
		return suggestedAction;
	}

	public void setSuggestedAction(String suggestedAction) {
		this.suggestedAction = suggestedAction;
	}

	public List<String> getEvidence() {
		return evidence;
	}

	public void setEvidence(List<String> evidence) {
		this.evidence = evidence;
	}

}
