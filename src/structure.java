SatyaScan/
│
├── 📂 backend/
│   ├── src/
│      ├── Main.java                   # Entry point of the Java backend
│      ├── controller/                 # Java classes to handle HTTP routes and API logic
│      ├── service/                    # Core logic: NLP processing, fact-check calls
│      └── utils/                      # Helper methods (e.g., string clean-up, encoding)
│   
│
├── 📂 api/
│   ├── factcheck/                      # Integrations with third-party Fact-Check APIs
│   └── responsehandler/                # JSON parsing and result formatting
│
├── 📂 nlp/
│   ├── TextPreprocessor.java           # Tokenization, stop-word removal, etc.
│   ├── ClaimClassifier.java            # Optional fake news classification logic
│   └── LanguageDetector.java           # Handles multilingual input (if used)
│
├── 📂 web/
│   ├── index.html                      # Main UI for user input
│   ├── styles.css                      # CSS styling for the front-end
│   └── assets/                         # Icons, images, or fonts
│
├── 📜 README.md                        # Project intro, setup, usage and Features
├── 📜 project-structure.md             # This file: structure explanation
├── 📜 LICENSE                          # MIT License file
