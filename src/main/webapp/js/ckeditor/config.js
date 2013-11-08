/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
    config.toolbar = 'SisdaToolbar';

    config.toolbar_SisdaToolbar =
        [
            { name: 'document',    items : [ 'Source','-', 'Templates' ] },
            { name: 'clipboard',   items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
            { name: 'editing',     items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
            { name: 'insert',      items : [ 'Table','HorizontalRule', 'Image', 'Smiley','SpecialChar','PageBreak' ] },
            { name: 'links',       items : [ 'Link','Unlink','Anchor' ] },
//            { name: 'forms',       items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
            '/',
            { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
            { name: 'paragraph',   items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
            { name: 'colors',      items : [ 'TextColor','BGColor' ] },
            { name: 'tools',       items : [ 'Maximize', 'ShowBlocks','-','About' ] },
        '/',
            { name: 'styles',      items : [ 'Styles','Format','Font','FontSize' ] },
        ];
};
