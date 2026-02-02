import React, { useState, useEffect } from 'react';
import { Settings, History, Delete, ArrowLeft } from 'lucide-react';
import { evaluateExpression, isErrorMessage } from './utils/evaluator';
import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

const App: React.FC = () => {
  const [expression, setExpression] = useState('');
  const [preview, setPreview] = useState('');
  const [history, setHistory] = useState<{ op: string; res: string }[]>([]);

  useEffect(() => {
    if (expression) {
      const result = evaluateExpression(expression);
      setPreview(result);
    } else {
      setPreview('');
    }
  }, [expression]);

  const handleAction = (val: string) => {
    if (val === '=') {
      if (preview && !isErrorMessage(preview)) {
        setHistory([{ op: expression, res: preview }, ...history.slice(0, 49)]);
        setExpression(preview);
      }
    } else if (val === 'C') {
      setExpression('');
    } else if (val === 'backspace') {
      setExpression(prev => prev.slice(0, -1));
    } else {
      setExpression(prev => prev + val);
    }
  };

  const buttons = [
    { text: '!', type: 'surface' },
    { text: '%', type: 'surface' },
    { text: '√', type: 'surface' },
    { text: 'π', type: 'surface' },
    { text: 'C', type: 'primary' },
    { text: '(', type: 'tonal' },
    { text: ')', type: 'tonal' },
    { text: '÷', type: 'tonal' },
    { text: '7', type: 'surface' },
    { text: '8', type: 'surface' },
    { text: '9', type: 'surface' },
    { text: '×', type: 'tonal' },
    { text: '4', type: 'surface' },
    { text: '5', type: 'surface' },
    { text: '6', type: 'surface' },
    { text: '-', type: 'tonal' },
    { text: '1', type: 'surface' },
    { text: '2', type: 'surface' },
    { text: '3', type: 'surface' },
    { text: '+', type: 'tonal' },
    { text: '0', type: 'surface' },
    { text: '.', type: 'surface' },
    { text: 'backspace', type: 'surface', icon: <Delete size={24} /> },
    { text: '=', type: 'primary' },
  ];

  return (
    <div className="flex justify-center items-center min-vh-100 p-4">
      {/* Mobile Frame */}
      <div className="w-[360px] h-[760px] bg-white rounded-[40px] shadow-2xl overflow-hidden border-8 border-cute-dark flex flex-col relative">

        {/* Top Bar */}
        <div className="p-6 flex justify-between items-center bg-white">
          <div className="w-8 h-8 rounded-full bg-cute-pink/20 flex items-center justify-center">
            <Settings className="text-cute-dark" size={18} />
          </div>
          <div className="flex gap-4">
            <History className="text-cute-dark" size={24} />
            <Settings className="text-cute-dark" size={24} />
          </div>
        </div>

        {/* Display */}
        <div className="flex-1 flex flex-col justify-end p-6 bg-white overflow-hidden">
          <div className="text-right text-cute-dark/60 text-xl font-medium truncate mb-2">
            {expression || ' '}
          </div>
          <div className={cn(
            "text-right text-cute-dark font-black tracking-tight break-all leading-none",
            expression.length > 8 ? "text-4xl" : "text-6xl"
          )}>
            {preview || (expression ? expression : '0')}
          </div>
        </div>

        {/* Keyboard */}
        <div className="p-4 grid grid-cols-4 gap-3 bg-white pb-8">
          {buttons.map((btn, i) => (
            <button
              key={i}
              onClick={() => handleAction(btn.text)}
              className={cn(
                "m3-button",
                btn.type === 'primary' && "m3-button-primary",
                btn.type === 'tonal' && "m3-button-tonal",
                btn.type === 'surface' && "m3-button-surface",
                btn.text === '=' && "m3-button-secondary"
              )}
            >
              {btn.icon || btn.text}
            </button>
          ))}
        </div>

        {/* Home Indicator */}
        <div className="absolute bottom-1 left-1/2 -translate-x-1/2 w-32 h-1 bg-cute-dark/20 rounded-full" />
      </div>
    </div>
  );
};

export default App;
