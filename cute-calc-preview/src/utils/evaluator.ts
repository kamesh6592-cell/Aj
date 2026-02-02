import { create, all } from 'mathjs';

const math = create(all);

export const evaluateExpression = (expression: string, precision: number = 8): string => {
    if (!expression) return "";

    try {
        // Replace custom symbols with mathjs compatible ones
        let processed = expression
            .replace(/×/g, '*')
            .replace(/÷/g, '/')
            .replace(/π/g, 'PI')
            .replace(/√(\d+(\.\d+)?)/g, 'sqrt($1)') // Basic sqrt support
            .replace(/(\d+(\.\d+)?)!/g, 'factorial($1)'); // Basic factorial support

        // Handle relative percentage logic from Evaluator.kt
        // regex: (\d+(?:\.\d+)?)\s*([+\-*])\s*(\d+(?:\.\d+)?)%
        const percentageRegex = /(\d+(?:\.\d+)?)\s*([+\-*])\s*(\d+(?:\.\d+)?)%/g;
        processed = processed.replace(percentageRegex, (_, a, op, b) => {
            if (op === '+') return `${a} + (${a} * ${b} / 100)`;
            if (op === '-') return `${a} - (${a} * ${b} / 100)`;
            if (op === '*') return `${a} * (${b} / 100)`;
            return a;
        });

        // Handle standalone percentage
        processed = processed.replace(/(\d+(\.\d+)?)%/g, '($1 / 100)');

        const result = math.evaluate(processed);

        if (typeof result === 'number') {
            if (!isFinite(result)) return "Error";

            // Format like the original app
            return Number(result.toFixed(precision)).toString();
        }

        return result.toString();
    } catch (e) {
        return ""; // Silent fail for partial expressions, like the original
    }
};

export const isErrorMessage = (text: string): boolean => {
    return /[a-zA-Z]/.test(text) && !['PI', 'Infinity', 'NaN'].includes(text);
};
