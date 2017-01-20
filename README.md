# RetroPreference

SharedPreferences wrapper inspired by Retrofit，类型安全的 SharedPreferences 工具。

* [x] Type Safe
* [x] Key 强一致
* [x] 混淆代码同时也会混淆 SharedPreferences 的文件 name 和 key name，并且不影响使用

### Sample Code

Define:

```java
public interface Settings {

    Preference<Integer> launche_count();

    Preference<Boolean> is_login();

    Preference<Float> user_points();

    Preference<Long> last_login();

    Preference<String> username();

    Preference<Set<String>> user_tags();
}
```

Save or read:

```java
// 建议在 Application onCreate 时创建唯一实例。
mSettings = RetroPreference.create(appContext, Settings.class, Context.MODE_PRIVATE);
// 保存启动次数
mSettings.launche_count().set(7);
// 获得存储的值
mSettings.launche_count().get();
// or
mSettings.launche_count().get(100); // 100 为默认值
```

更多用例参考[测试用例](https://github.com/twiceyuan/RetroPreference/blob/master/retropreference/src/androidTest/java/com/twiceyuan/library/ExampleInstrumentedTest.java)

## License

```
Copyright 2016 twiceYuan.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
