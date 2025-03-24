<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Translation;

class TranslationController extends Controller
{
    public function translate(Request $request)
    {
        $request->validate([
            'source_language' => 'required|string|in:TL',
            'target_language' => 'required|string|in:PAG,ILO',
            'source_text' => 'required|string',
        ]);

        $translation = Translation::where('source_language', $request->source_language)
            ->where('target_language', $request->target_language)
            ->where('source_text', $request->source_text)
            ->first();

        if (!$translation) {
            return response()->json(['error' => 'Translation not found'], 404);
        }

        return response()->json($translation);
    }

    public function history()
    {
        $history = Translation::where('source_language', 'TL')
            ->whereIn('target_language', ['PAG', 'ILO'])
            ->latest()
            ->get();

        return response()->json($history);
    }
}
