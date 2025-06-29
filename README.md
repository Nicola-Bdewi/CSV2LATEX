# 📄 CSV to LaTeX Converter

This Java application converts a `.CSV` file into a LaTeX-formatted `.TEX` table. It validates the input CSV for missing attributes and data, generates a log file when needed, and allows you to preview the resulting LaTeX in the console.

---

## Features

- Converts `.CSV` files to `.TEX` (LaTeX table format)
- Detects and logs missing attributes (stops conversion)
- Handles missing data gracefully by inserting `***`
- Creates `log.txt` for invalid entries
- Lets you view the generated `.TEX` file in the console

---

## 🛠️ How to Run

### 1. Go to the CSV2LATEX\CSV2LATEX\src path
### 2. Run `javac *.java`
### 3. Run `java Driver`


## 🧑‍💻 Usage Instructions

### 1: Enter Path to CSV File

You will see this prompt: `Enter the path of a CSV file to convert it to TEX:`

### 2: Enter the full path **without** the `.CSV` extension

Example input: `path/EmployerInterviewCVS`

### 3: Enter Path to Read the Generated .TEX File

Then you will see: `Enter the path of the file you would like to read:`

### 4: Enter the full path **with** the `.TEX` extension.

Example input: `path/EmployerInterviewCVS.TEX`

Example file:
```
Employer Interview for Future Hiring
Interview Date,CEO Name,Num of Employees,Industry,City/Province,Hiring
11/5/2020,Adam,326,Software Development,Laval/ Que,Yes
14/05/2020,Steve,1512,Retail Store,Ottawa/ON,NO
3/4/2020,Sami,720,Car dealer,Toronto/ON,NO
3/2/2020,Jim,142,Traver Agency,Montreal/Que,Yes
```

---

### 🖨️ Sample Output

If your input CSV is valid, the following LaTeX code will be printed to the console:

```latex
\begin{table}[]
	\begin{tabular}{l|l|l|l|l|l|}
		\toprule
		Interview Date & CEO Name & Num of Employees & Industry & City/Province & Hiring \\ \midrule
		11/5/2020 & Adam & 326 & Software Development & Laval/ Que & Yes \\ \midrule
		14/05/2020 & Steve & 1512 & Retail Store & Ottawa/ON & NO \\ \midrule
		3/4/2020 & Sami & 720 & Car dealer & Toronto/ON & NO \\ \midrule
		3/2/2020 & Jim & 142 & Traver Agency & Montreal/Que & Yes \\ \bottomrule
	\end{tabular}
	\caption{Employer Interview for Future Hiring}
	\label{EmployerInterviewCVS}
\end{table}
```

