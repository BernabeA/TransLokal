<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\TranslationController;

Route::post('/translate', [TranslationController::class, 'translate']);
Route::get('/history', [TranslationController::class, 'history']);


Route::get('/user', function (Request $request) {
    return $request->user();
})->middleware('auth:sanctum');
