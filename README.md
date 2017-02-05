# MaterialButton

Backported material styled Button for use on pre-lollipop devices. Supports Android 2.3 API 9 (GINGERBREAD) and up.<br />
Also check out https://github.com/robertapengelly/android-material-textview as MaterialButton extends MaterialTextView.

Preview

![materialbuttonexample](https://cloud.githubusercontent.com/assets/5245027/22246905/08f32292-e22f-11e6-9472-9aaf5badbccd.gif)

# Installation

    Step 1. Add the JitPack repository to your build file
    
    Add it in your root build.gradle at the end of repositories:
    
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
         }
    }
    
    Step 2. Add the dependency
    
    dependencies {
        compile 'com.github.robertapengelly:android-material-button:1.0.2'
    }

# Usage

    Styling
    
    The following style attributes are lollipop defaults for the theme. Change the values to better suit your app.
    
    Pre-Honycomb devices (values/styles.xml)
    
        <style name="AppTheme" parent="@android:style/Theme.NoTitleBar">
            <!-- Text colors -->
            <item name="android:textColorPrimary">@color/primary_text_material_dark</item>
            <item name="android:textColorSecondary">@color/secondary_text_material_dark</item>
            <item name="android:textColorTertiary">@color/secondary_text_material_dark</item>
            <item name="android:textColorHint">@color/hint_foreground_material_dark</item>
            <item name="android:textColorHighdark">@color/highdarked_text_material_dark</item>
            <item name="android:textColorLink">@color/link_text_material_dark</item>
            
            <item name="android:textAppearanceButton">@style/TextAppearance.Material.Button</item>
            <item name="android:buttonStyle">@style/Widget.Material.Button</item>
            
            <!-- Color palette -->
            <item name="colorButtonNormal">@color/btn_default_material_dark</item>
            <item name="colorControlHighlight">@color/ripple_material_dark</item>
        </style>
    
    Honycomb and newer (values-v11/styles.xml)
    
        <style name="AppTheme" parent="@android:style/Theme.Holo.NoActionBar">
            <!-- Text colors -->
            <item name="android:textColorPrimary">@color/primary_text_material_dark</item>
            <item name="android:textColorSecondary">@color/secondary_text_material_dark</item>
            <item name="android:textColorTertiary">@color/secondary_text_material_dark</item>
            <item name="android:textColorHint">@color/hint_foreground_material_dark</item>
            <item name="android:textColorHighdark">@color/highdarked_text_material_dark</item>
            <item name="android:textColorLink">@color/link_text_material_dark</item>
            
            <item name="android:textAppearanceButton">@style/TextAppearance.Material.Button</item>
            <item name="android:buttonStyle">@style/Widget.Material.Button</item>
            
            <!-- Color palette -->
            <item name="colorButtonNormal">@color/btn_default_material_dark</item>
            <item name="colorControlHighlight">@color/ripple_material_dark</item>
        </style>
    
    Lollipop and newer (values-v21/styles.xml)
    
        <style name="AppTheme" parent="@android:style/Theme.Material.NoActionBar">
            <!-- Text colors -->
            <item name="android:textColorPrimary">@color/primary_text_material_dark</item>
            <item name="android:textColorSecondary">@color/secondary_text_material_dark</item>
            <item name="android:textColorTertiary">@color/secondary_text_material_dark</item>
            <item name="android:textColorHint">@color/hint_foreground_material_dark</item>
            <item name="android:textColorHighdark">@color/highdarked_text_material_dark</item>
            <item name="android:textColorLink">@color/link_text_material_dark</item>
            
            <item name="android:textAppearanceButton">@style/TextAppearance.Material.Button</item>
            <item name="android:buttonStyle">@style/Widget.Material.Button</item>
            
            <!-- Color palette -->
            <item name="android:colorButtonNormal">@color/btn_default_material_dark</item>
            <item name="android:colorControlHighlight">@color/ripple_material_dark</item>
        </style>
    
    Adding a MaterialButton widget (layout/activity_main.xml)
    
    If you use android:elevation it will be replaced with app:elevation.
    If you use android:textAllCaps it will be replaced with app:textAllCaps.
    
    If you want to use a ripple background on all-devices add app:background="@drawable/your_drawable_name" and it will be inflated.
    When you use app:background it will only take effect if there's no background.
    
        <?xml version="1.0" encoding="utf-8" ?>
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_height="match_parent"
            android:layout_width="match_parent"
            android:orientation="vertical">
            
            <robertapengelly.support.widget.MaterialButton
                android:layout_height="wrap_content"
                android:layout_width="match_parent"
                android:text="MaterialButton"
                app:elevation="4dp"
                app:textAllCaps="false" />
        
        </LinearLayout>
