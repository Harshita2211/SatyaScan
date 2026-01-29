# SatyaScan

**"Fake or fact, we’ve got your back — SatyaScan on track."**

Real-time Detection of Fake News on Websites or Social Media using NLP and Fact-Check APIs

**Problem Statement:-**
The rapid spread of misinformation and fake news on websites and social media platforms poses a serious threat to public opinion, democratic processes, and public safety. Manual verification is time-consuming and ineffective against the scale of digital content being published every second.

**Our Solution:-**
We propose an intelligent, real-time system that detects and flags potentially fake news content using Natural Language Processing (NLP) and external fact-check APIs. The user will enter the news into the system, analyze its credibility using API's and provides a confidence score and supporting evidence or rebuttals from verified sources.

**Tech Stack:-**
- Backend: Java (Servlets or Spring)
- NLP: OpenNLP / CoreNLP 
- APIs: Google Fact Check Tools API, Claimbuster API
- Frontend : HTML/CSS(or simple Java GUI)
- IDE/Editor: VS Code, Notepad++
- Deployment: Localhost via Apache Tomcat

##  Project Structure
  ```SatyaScan/
├── backend/
│ └── src/
│ ├── Main.java # Entry point of the Java backend
│ ├── controller/ # Handles HTTP routes and API logic
│ ├── service/ # Core logic: NLP processing, fact-check calls
│ ├── utils/ # Helper methods (string clean-up, encoding)
│ ├── api/
│ │ └── factcheck/ # Integrations with third-party Fact-Check APIs
│ ├── responsehandler/ # JSON parsing and result formatting
│ └── nlp/
│ ├── TextPreprocessor.java # Tokenization, stop-word removal, etc.
│ ├── ClaimClassifier.java # Optional fake news classification logic
│ └── LanguageDetector.java # Handles multilingual input
├── web/
│ ├── index.html # Main UI for user input
│ ├── styles.css # CSS styling for the front-end
│ └── assets/ # Icons, images, fonts
├── docs/
│ ├── architecture.png # System architecture diagram
│ └── SatyaScan_Presentation.pdf # Final pitch / submission report
├── README.md # Project intro, setup, usage, features
├── project-structure.md # Structure explanation
└── LICENSE # MIT License
```

**Goals / Features to Implement:-**
-  Accept input from website content or social media posts
-  Extract and clean text using NLP
-  Identify key claims or statements
-  Query fact-check APIs for validation
-  Provide a credibility score and visual feedback
-  Log suspicious content for further review

 **Team Members:-**
- SHREYANSH JAIN – Backend Developer
- LAKSHAY BISHT – Research and Presentation Development 
- ANSHIKA AGGARWAL– Frontend Developer
- HARSHITA GUPTA – GitHub Manager 






